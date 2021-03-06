package spreadsheet.mapper.w2o.validation.rule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import spreadsheet.mapper.w2o.param.BooleanParam;
import spreadsheet.mapper.w2o.param.NumberScaleRangeParam;
import spreadsheet.mapper.w2o.validation.validator.cell.DependencyValidator;
import spreadsheet.mapper.w2o.validation.validator.cell.MultiCellValidator;
import spreadsheet.mapper.w2o.validation.validator.cell.SingleCellValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * Created by hanwen on 2017/1/23.
 */
public class DefaultDependencyValidatorBuilderTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDependencyValidatorBuilderTest.class);

  @BeforeClass
  public void before() {
    LOGGER.debug("-------------------starting test dependency validator builder-------------------");
  }

  @Test
  public void testBuilder() throws Exception {

    DependencyValidatorBuilder builder = new DefaultDependencyValidatorBuilder();

    List<DependencyValidator> validators = builder
        .single("bool")
        .errorMessage("test")
        .matchFields("t1")
        .group("t1")
        .dependsOn("t2", "t3")
        .param(
            new BooleanParam()
                .supportedTrue("y")
                .supportedFalse("n")
        )
        .end()

        .single("numberScaleRange")
        .errorMessage("test")
        .group("t2")
        .matchFields("t2")
        .param(
            new NumberScaleRangeParam()
                .gte(0)
                .lte(2)
        )
        .end()

        .single("localDateTime")
        .errorMessage("test")
        .group("t3")
        .matchFields("t3")
        .param("yyyy-MM-dd HH:mm:ss")
        .end()

        .multi("multiUnique")
        .group("t4")
        .dependsOn("t1")
        .errorMessage("test")
        .matchFields("t4", "t5")
        .end()

        .single("digits")
        .matchFields("t6", "t7")
        .end()

        .single("require")
        .matchFields("t1", "t2", "t3", "t4")
        .end()

        .single("number")
        .matchFields("t8")
        .dependsOn("t7")
        .errorMessage("test")
        .end()

        .single("regex")
        .matchFields("t9")
        .errorMessage("test")
        .group("t9")
        .dependsOn("t8")
        .param("^[1-9]\\d*$")
        .end()

        .single("localDate")
        .matchFields("t10")
        .errorMessage("test")
        .group("t10")
        .param("yyyy-MM-dd")
        .end()

        .single("unique")
        .matchFields("t11")
        .end()

        .build();

    assertEquals(validators.size(), 14);

    {
      SingleCellValidator v1 = (SingleCellValidator) validators.get(0);
      assertEquals(v1.getGroup(), "t1");
      assertEquals(v1.getMatchField(), "t1");
      assertEquals(v1.getErrorMessage(), "test");
      assertEquals(v1.getDependsOn(), new LinkedHashSet<>(Arrays.asList("t2", "t3")));
    }

    {
      SingleCellValidator v2 = (SingleCellValidator) validators.get(1);
      assertEquals(v2.getGroup(), "t2");
      assertEquals(v2.getMatchField(), "t2");
      assertEquals(v2.getErrorMessage(), "test");
      assertEquals(v2.getDependsOn().size(), 0);
    }

    {
      SingleCellValidator v3 = (SingleCellValidator) validators.get(2);
      assertEquals(v3.getGroup(), "t3");
      assertEquals(v3.getMatchField(), "t3");
      assertEquals(v3.getErrorMessage(), "test");
      assertEquals(v3.getDependsOn().size(), 0);
    }

    {
      MultiCellValidator v4 = (MultiCellValidator) validators.get(3);
      assertEquals(v4.getGroup(), "t4");
      assertEquals(v4.getMatchFields(), new LinkedHashSet<>(Arrays.asList("t4", "t5")));
      assertEquals(v4.getErrorMessage(), "test");
      assertEquals(v4.getDependsOn(), new LinkedHashSet<>(Collections.singleton("t1")));
    }

    {
      SingleCellValidator v5 = (SingleCellValidator) validators.get(4);
      assertEquals(v5.getGroup(), "t6");
      assertEquals(v5.getMatchField(), "t6");
      assertEquals(v5.getDependsOn().size(), 0);
      assertNull(v5.getErrorMessage());
    }

    {
      SingleCellValidator v6 = (SingleCellValidator) validators.get(5);
      assertEquals(v6.getGroup(), "t7");
      assertEquals(v6.getMatchField(), "t7");
      assertEquals(v6.getDependsOn().size(), 0);
      assertNull(v6.getErrorMessage());
    }

    {
      SingleCellValidator v7 = (SingleCellValidator) validators.get(6);
      assertEquals(v7.getGroup(), "t1");
      assertEquals(v7.getMatchField(), "t1");
      assertEquals(v7.getDependsOn().size(), 0);
      assertNull(v7.getErrorMessage());
    }

    {
      SingleCellValidator v8 = (SingleCellValidator) validators.get(7);
      assertEquals(v8.getGroup(), "t2");
      assertEquals(v8.getMatchField(), "t2");
      assertEquals(v8.getDependsOn().size(), 0);
      assertNull(v8.getErrorMessage());
    }

    {
      SingleCellValidator v9 = (SingleCellValidator) validators.get(8);
      assertEquals(v9.getGroup(), "t3");
      assertEquals(v9.getMatchField(), "t3");
      assertEquals(v9.getDependsOn().size(), 0);
      assertNull(v9.getErrorMessage());
    }

    {
      SingleCellValidator v10 = (SingleCellValidator) validators.get(9);
      assertEquals(v10.getGroup(), "t4");
      assertEquals(v10.getMatchField(), "t4");
      assertEquals(v10.getDependsOn().size(), 0);
      assertNull(v10.getErrorMessage());
    }

    {
      SingleCellValidator v11 = (SingleCellValidator) validators.get(10);
      assertEquals(v11.getGroup(), "t8");
      assertEquals(v11.getMatchField(), "t8");
      assertEquals(v11.getErrorMessage(), "test");
      assertEquals(v11.getDependsOn(), new LinkedHashSet<>(Collections.singleton("t7")));
    }

    {
      SingleCellValidator v12 = (SingleCellValidator) validators.get(11);
      assertEquals(v12.getGroup(), "t9");
      assertEquals(v12.getMatchField(), "t9");
      assertEquals(v12.getErrorMessage(), "test");
      assertEquals(v12.getDependsOn(), new LinkedHashSet<>(Collections.singleton("t8")));
    }

    {
      SingleCellValidator v13 = (SingleCellValidator) validators.get(12);
      assertEquals(v13.getGroup(), "t10");
      assertEquals(v13.getMatchField(), "t10");
      assertEquals(v13.getErrorMessage(), "test");
      assertEquals(v13.getDependsOn().size(), 0);
    }

    {
      SingleCellValidator v14 = (SingleCellValidator) validators.get(13);
      assertEquals(v14.getGroup(), "t11");
      assertEquals(v14.getMatchField(), "t11");
      assertNull(v14.getErrorMessage());
      assertEquals(v14.getDependsOn().size(), 0);
    }

  }

}