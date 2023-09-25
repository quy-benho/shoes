package shoe.store.util;

import java.util.List;

import org.springframework.data.domain.Page;

import shoe.store.entity.BaseEntity;

public class Pagination<E extends BaseEntity> {

	private Page<E> page;
	private int pageNumber;
	private int totalPages;

	public int fix(String pageRequest) {

		int pageNumber = 0;
		if (pageRequest != null && pageRequest.matches("[0-9]+")) {
			pageNumber = Integer.valueOf(pageRequest) - 1;
		}
		return pageNumber < 0 ? 0 : pageNumber;
	}

	public void setPagination(Page<E> page) {
		this.page = page;
		this.pageNumber = page.getContent().isEmpty() ? 1 : page.getNumber() + 1;
		this.totalPages = page.getContent().isEmpty() ? 1 : page.getTotalPages();
	}

	public List<E> getContent() {
		return page.getContent();
	}

	public int getPageNumber() {
		return this.pageNumber;
	}

	public int getTotalPages() {
		return this.totalPages;
	}
}
