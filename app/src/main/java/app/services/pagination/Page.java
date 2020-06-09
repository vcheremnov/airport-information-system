package app.services.pagination;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {

    @SerializedName("content")
    private List<T> elementList;

    private Long totalPages;
    private Long totalElements;

    private Long size;
    private Long number;
    private Long numberOfElements;

    @SerializedName("first")
    private Boolean isFirst;

    @SerializedName("last")
    private Boolean isLast;

    @SerializedName("empty")
    private Boolean isEmpty;

    public interface ContentMapper<X, Y> {
        Y map(X item);
    }

    public <Y> Page<Y> map(ContentMapper<T, Y> contentMapper) {
        List<Y> mappedContent = elementList.stream()
                .map(contentMapper::map)
                .collect(Collectors.toList());

        return new Page<>(
                mappedContent,
                totalPages,
                totalElements,
                size,
                number,
                numberOfElements,
                isFirst,
                isLast,
                isEmpty
        );
    }

}
