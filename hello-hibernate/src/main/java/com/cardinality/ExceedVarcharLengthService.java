package com.cardinality;

import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Size;
import java.util.Set;

interface Repository extends JpaRepository<SampleClass, Long> {}

/**
 * Size (validation) - default max    = Integer.MAX_VALUE
 * Column            - default length = 255
 */
@Component
public class ExceedVarcharLengthService
{
    Repository repository;

    public ExceedVarcharLengthService(Repository repository)
    {
        this.repository = repository;
        exceed(repository);
    }

    /**
     * causes:
     * org.h2.jdbc.JdbcSQLDataException: Value too long for column "PROPERTY_WITH_NO_SIZE_SPECIFIED CHARACTER VARYING(255)"
     */
    private void exceed(Repository repository)
    {
        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";

        SampleClass sc1 = new SampleClass();
//        sc1.setPropertyWithNoSizeSpecified(loremIpsum); // org.h2.jdbc.JdbcSQLDataException: Value too long for column "PROPERTY_WITH_NO_SIZE_SPECIFIED CHARACTER VARYING(255)"
//        sc1.setXx(loremIpsum);
//        sc1.setXo(loremIpsum);
//        sc1.setOx(loremIpsum);
        sc1.setOo(loremIpsum); //ok
        repository.save(sc1); //will fail when we add spring-boot-starter-validation dependency
    }

    public Set<ConstraintViolation<SampleClass>> violations(SampleClass sampleClass)
    {
        try(ValidatorFactory vf = Validation.buildDefaultValidatorFactory()) {

            Set<ConstraintViolation<SampleClass>> violations = vf.getValidator().validate(sampleClass);

            if (!violations.isEmpty()) {
                violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .forEach(System.out::println);
            }

            return violations;
        }
    }
}

@Setter
@Entity
class SampleClass
{
    @Id
    @GeneratedValue
    Long id;

    private String propertyWithNoSizeSpecified;

    @Size(min = 3, max = 255)
    @Column
    private String xx;

    @Size(min = 3, max = 255)
    @Column(length = 500) // resolves: org.h2.jdbc.JdbcSQLDataException: Value too long for column "PROPERTY_WITH_SIZE_SPECIFIED CHARACTER VARYING(255)"
    private String xo;

    @Size
    @Column
    private String ox;

    @Size
    @Column(length = 500) // resolves: org.h2.jdbc.JdbcSQLDataException: Value too long for column "PROPERTY_WITH_SIZE_SPECIFIED CHARACTER VARYING(255)"
    private String oo;
}