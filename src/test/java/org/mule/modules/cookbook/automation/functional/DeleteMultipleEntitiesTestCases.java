package org.mule.modules.cookbook.automation.functional;

import com.cookbook.tutorial.service.CookBookEntity;
import com.cookbook.tutorial.service.NoSuchEntityException;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.cookbook.exception.CookbookException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.fail;

public class DeleteMultipleEntitiesTestCases extends AbstractTestCases {

    private List<Integer> entityIds;

    @Before
    public void setUp() throws CookbookException {
        List<CookBookEntity> createdEntities = getConnector().createMultipleEntities(TestDataBuilder.createMultipleEntitiesData());
        entityIds = Lists.transform(createdEntities, new Function<CookBookEntity, Integer>() {

            @Override
            public Integer apply(final CookBookEntity input) {
                return input.getId();
            }
        });
    }

    @Test
    public void testDeleteMultipleIngredients() throws CookbookException {
        getConnector().deleteMultipleEntities(entityIds);
        try {
            getConnector().getMultipleEntities(entityIds);
            fail();
        } catch(CookbookException e){
            assertThat(e.getCause(), instanceOf(NoSuchEntityException.class));
        }
    }

    @Test
    public void testDeleteMultipleIngredientsNotFound() throws CookbookException {
        try{
            List<Integer> copyOfIds = Lists.newArrayList(entityIds);
            copyOfIds.add(-1);
            getConnector().deleteMultipleEntities(copyOfIds);
            fail();
        } catch(CookbookException e){
            assertThat(e.getCause(), instanceOf(NoSuchEntityException.class));
        }

    }


}
