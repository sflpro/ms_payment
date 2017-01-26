package com.sfl.pms.api.internal.facade.test;

import com.sfl.pms.api.internal.facade.helper.FacadeImplTestHelper;
import com.sfl.pms.core.api.internal.model.common.result.ErrorResponseModel;
import com.sfl.pms.core.api.internal.model.common.result.ErrorType;
import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User: Alfred Kaghyan
 * Company: SFL LLC
 * Date: 5/13/2015
 * Time: 3:52 PM
 */
@RunWith(EasyMockRunner.class)
@Ignore
public abstract class AbstractFacadeUnitTest extends EasyMockSupport {

    /* Properties */
    private final FacadeImplTestHelper FacadeImplTestHelper;


    public AbstractFacadeUnitTest() {
        FacadeImplTestHelper = new FacadeImplTestHelper();
    }

    /* Utility methods */
    protected void assertValidationErrors(final List<ErrorResponseModel> errors, final Set<ErrorType> errorTypes) {
        assertNotNull(errors);
        assertNotNull(errorTypes);
        errors.forEach(errorResponseModel -> assertTrue(errorTypes.contains(errorResponseModel.getErrorType())));
    }

    /* Getters and setters */
    public FacadeImplTestHelper getFacadeImplTestHelper() {
        return FacadeImplTestHelper;
    }
}
