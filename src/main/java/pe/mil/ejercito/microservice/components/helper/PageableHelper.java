package pe.mil.ejercito.microservice.components.helper;

import com.bxcode.tools.loader.componets.helpers.CommonHelper;
import com.bxcode.tools.loader.dto.PageableDto;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * PageableHelper
 * <p>
 * PageableHelper class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 10/03/2024
 */
@UtilityClass
public class PageableHelper {

    public static void generatePaginationDetails(Page<?> entityPage, String page, String limit, PageableDto pageable) {
        final BigInteger totalRecords = CommonHelper.longToBigInteger(entityPage.getTotalElements());
        final BigDecimal lastPage = CommonHelper.getLastPage(totalRecords, limit);
        final BigInteger nextPageNumber = CommonHelper.getNextPageNumber(page);
        final BigInteger previousPageNumber = CommonHelper.previousPageNumber(page);
        final String nextPageNumberAsString = CommonHelper.getNextPageNumber(nextPageNumber, lastPage.toBigInteger(), limit);
        final String previousPageNumberAsString = CommonHelper.getPreviousPageNumber(previousPageNumber, lastPage.toBigInteger(), limit);
        final String lastPageAsString = CommonHelper.getLastPage(lastPage, limit);
        final String firstPageAsString = CommonHelper.getFirstPage(totalRecords, limit);

        pageable.setTotal(String.valueOf(entityPage.getTotalElements()));
        pageable.setLimit(limit);
        pageable.setCurrentPage(page);
        pageable.setFirstPageUrl(firstPageAsString);
        pageable.setLastPageUrl(lastPageAsString);
        pageable.setNextPageUrl(nextPageNumberAsString);
        pageable.setPrevPageUrl(previousPageNumberAsString);
        pageable.setLastPage(String.valueOf(lastPage));
    }
}


