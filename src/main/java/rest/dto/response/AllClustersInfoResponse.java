package rest.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class AllClustersInfoResponse {
    List<ClusterInfoResponse> content;
    public Pageable pageable;
    public boolean last;
    public int totalElements;
    public int totalPages;
    public Sort sort;
    public boolean first;
    public int numberOfElements;
    public int size;
    public int number;
    public boolean empty;

    public class Sort {
        public boolean sorted;
        public boolean unsorted;
        public boolean empty;
    }

    public class Pageable {
        public Sort sort;
        public int pageNumber;
        public int pageSize;
        public int offset;
        public boolean paged;
        public boolean unpaged;
    }
}
