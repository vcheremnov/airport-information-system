package app.services.pagination;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
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

}
