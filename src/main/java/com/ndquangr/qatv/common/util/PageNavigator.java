package com.ndquangr.qatv.common.util;

/*******************************************************************************
EX)
// 생성자 호출
PageNavigator pageNavigator = new PageNavigator(
	cPage						- 페이지번호
	,"/xxx/xxxL.do"				- URL
	,pagePerBlock				- 블럭당 페이지 수
	,recordPerPage				- 페이지당 레코드 수
	,totalCount					- 레코드의 총 수
	,"&srchItem="+srchItem+"&srchText="+ParameterUtil.getEncode(srchText));
								- 파라미터
int startIndex = pageNavigator.getRecordPerPage() * (cPage - 1);
시작위치, 페이지당 레코드 수, Map을 매개변수로 전달
List list = (List)xxxService.getList(startIndex, 
	pageNavigator.getRecordPerPage(), Map args);
xxxService 에서  원하는 레코드위치를 가져오는 메소드를 이용하여 목록을 얻음. (AbstractDao, Dao getPageList 추가)
queryForList("xxx.getPageList", hashMap, startIndex, recordPerPage);
******************************************************************************/
public class PageNavigator {
	
	// -------------------------------------------------------------------------
	private int totalRecord;										// 전체 레코드수
	private int recordPerPage;										// 페이지당 레코드수
	private int pagePerBlock;										// 블럭당 페이지수
	private int currentPage;										// 현재페이지
	private String goUrl;											// 이동할 URL
	private String parameter;										// 파라미터
	private int totalNumOfPage;								//전체 페이지
	// -------------------------------------------------------------------------	
	
	public PageNavigator(){}
	
	/**
	 * 생성자
	 * @param currentPage 	- 현재 페이지 번호
	 * @param goUrl 		- 목록 액션 URL
	 * @param pagePerBlock 	- 페이지당 블록 수
	 * @param recordPerPage - 한 페이지당 목록 수
	 * @param totalRecord 	- 총 레코드 수
	 * @param parameter 	- 파라미터
	 */
	public PageNavigator (int currentPage, String goUrl, int pagePerBlock,
			int recordPerPage, int totalRecord, String parameter){
		super();
		this.currentPage = currentPage;
		this.goUrl = goUrl;
		this.pagePerBlock = pagePerBlock;
		this.recordPerPage = recordPerPage;
		this.totalRecord = totalRecord;
		this.parameter  = parameter;
		this.totalNumOfPage = (totalRecord % recordPerPage == 0) ? 
				totalRecord / recordPerPage :
				totalRecord / recordPerPage + 1;
	}

	/**
	 * 페이지 네비게이터 생성
	 * @return 네비게이터 HTML 
	 */
	public String getMakePage() {
		if(totalRecord == 0) return "&nbsp;";
		
		int totalNumOfBlock = (totalNumOfPage % pagePerBlock == 0) ?
				totalNumOfPage / pagePerBlock :
				totalNumOfPage / pagePerBlock + 1;
		
		int currentBlock = (currentPage % pagePerBlock == 0) ? 
				currentPage / pagePerBlock :
				currentPage / pagePerBlock + 1;
		
		int startPage = (currentBlock - 1) * pagePerBlock + 1;
		int endPage = startPage + pagePerBlock - 1;
		
		if(endPage > totalNumOfPage)
			endPage = totalNumOfPage;
		
		boolean isNext = false;
		boolean isPrev = false;
		
		if(currentBlock < totalNumOfBlock)
			isNext = true;
		if(currentBlock > 1)
			isPrev = true;
		if(totalNumOfBlock == 1){
			isNext = false;
			isPrev = false;
		}
		
		StringBuffer sb = new StringBuffer();

        sb.append("<div class='text-center'>");
        sb.append("<ul class='pagination pagination-sm'>");

        if(currentPage > 1){
            sb.append("<li><a href=\"").append(goUrl);
            sb.append("?cPage=1").append(parameter).append("\" aria-label=\"First page\"><span aria-hidden=\"true\">&laquo;&laquo;</span></a></li>");
        }

        if (isPrev) {
            int goPrevPage = startPage - pagePerBlock;
            sb.append("<li><a href=\"").append(goUrl);
            sb.append("?cPage=").append(goPrevPage).append(parameter).append("\" aria-label=\"Previous\"><span aria-hidden=\"true\">&laquo;</span></a></li>");
        }

        for (int i = startPage; i <= endPage; i++) {

            if (i == currentPage) {
                //sb.append("<li class=\"active\"><a href=\"#\">").append(i).append("<span class=\"sr-only\">(current)</span></a></li> ");
            	sb.append("<li class=\"active\"><a href=\"#\">").append(i).append("<span class=\"sr-only\"></span></a></li> ");
            } else {
                sb.append("<li><a href=\"").append(goUrl);
                sb.append("?cPage=").append(i).append(parameter).append("\">").append(i).append(
                        "</a></li>");
            }
        }

        if (isNext) {
            int goNextPage = startPage + pagePerBlock;
            sb.append("<li><a href=\"").append(goUrl);
            sb.append("?cPage=").append(goNextPage).append(parameter).append("\" aria-label=\"Next\"><span aria-hidden=\"true\">&raquo;</span></a></li>");
        }

        if(totalNumOfPage > currentPage){
            sb.append("<li><a href=\"").append(goUrl);
            sb.append("?cPage=").append(totalNumOfPage).append(parameter).append("\" aria-label=\"Last page\"><span aria-hidden=\"true\">&raquo;&raquo;</span></a></li>");
        }

        sb.append("</div>");
        sb.append("</ul>");

        return sb.toString();
	}

	public int getTotalNumOfPage() {
		return totalNumOfPage;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getRecordPerPage() {
		return recordPerPage;
	}
	public void setRecordPerPage(int recordPerPage) {
		this.recordPerPage = recordPerPage;
	}
	public int getPagePerBlock() {
		return pagePerBlock;
	}
	public void setPagePerBlock(int pagePerBlock) {
		this.pagePerBlock = pagePerBlock;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public String getGoUrl() {
		return goUrl;
	}
	public void setGoUrl(String goUrl) {
		this.goUrl = goUrl;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
