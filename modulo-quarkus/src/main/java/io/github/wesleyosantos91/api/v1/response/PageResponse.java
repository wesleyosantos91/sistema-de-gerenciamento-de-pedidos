package io.github.wesleyosantos91.api.v1.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.github.wesleyosantos91.domain.commons.PageModel;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PageResponse<T> {
    public List<T> content;
    private Page page;

    public PageResponse(List<T> content, Page page) {
        this.content = content;
        this.page = page;
    }

    public PageResponse(PageModel<T> pageModel) {
        this.content = pageModel.getData();
        this.page = new Page(pageModel.getPage(), pageModel.getSize(), pageModel.getTotalCount());
    }

    public List<T> getContent() {
        return content;
    }

    public Page getPage() {
        return page;
    }
    public static class Page {
        private int size;
        private int number;
        private long totalElements;
        private int totalPages;

        public Page(int number, int size, long totalElements) {
            this.number = number;
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = (int) Math.ceil((double) totalElements / size);
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(long totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }
    }
}
