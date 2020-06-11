package app.services.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {

    private Long pageNumber;
    private Long pageSize;
    private PageSort pageSort;

    public static Map<String, Object> toMap(PageInfo pageInfo) {
        Map<String, Object> pageInfoMap = new HashMap<>();
        if (pageInfo != null) {
            pageInfoMap.computeIfAbsent("page", k -> pageInfo.pageNumber);
            pageInfoMap.computeIfAbsent("size", k -> pageInfo.pageSize);
            pageInfoMap.computeIfAbsent("sort", k -> pageInfo.pageSort);
        }

        return pageInfoMap;
    }

    public static PageInfo getUnlimitedPageInfo() {
        return new PageInfo(0L, (long) Integer.MAX_VALUE, new PageSort());
    }

}
