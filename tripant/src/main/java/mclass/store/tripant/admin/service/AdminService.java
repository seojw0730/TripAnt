package mclass.store.tripant.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mclass.store.tripant.admin.domain.AdminBoardEntity;
import mclass.store.tripant.admin.domain.AdminMemEntity;
import mclass.store.tripant.admin.domain.AdminStoreEntity;
import mclass.store.tripant.admin.model.repository.AdminRepository;

@Service
public class AdminService {
	
	@Autowired
	private AdminRepository admindao;
	
	//회원리스트
	public Map<String, Object> selectMemList( int memNum, int memPageNum, int currentPageNum, String searchMem) {					
		
		//현재페이지: currentPage
		// 하단에 표시할 페이지 수: memPageNum
		//화면에 한번에 표시되는  글 수 : memNum	
		Map<String, Object> result = null;
		
		//총 게시글 개수
		//DB가서 그때그때 알아와야함 - 호텔 한개 당 리뷰글이 몇개냐에 따라 달라질 수 있음
		int totalCount = admindao.totalCount();
		
		int startRownum = memNum * (currentPageNum - 1) + 1;
		int endRownum = memNum * currentPageNum;
		
//		전체페이지수(총 게시글 개수/한 페이지 당 글 수) => (총 게시글 개수%한 페이지 당 글 수== 0)?(총 게시글 개수/한 페이지 당 글 수):(총 게시글 개수/한 페이지 당 글 수+1)
		int totalPageCount = (totalCount % memNum == 0) ? (totalCount / memNum) : (totalCount / memNum) + 1;
		// 조건문 - 앞에가 0이 맞으면 : 앞에꺼, 0이 아니면 : 뒤에꺼
		
		//시작페이지
		int startPageNum = (currentPageNum % memPageNum == 0) ? ((currentPageNum / memPageNum) - 1) * memPageNum + 1
				: (currentPageNum / memPageNum) * memPageNum + 1;
		
		//끝페이지
		int endPageNum = (startPageNum + memPageNum > totalPageCount) ? totalPageCount : startPageNum + memPageNum - 1;
		
		List<AdminMemEntity> memList = admindao.selectMemList(startRownum, endRownum, searchMem);
		result = new HashMap<String, Object>();
		result.put("memList", memList);
		result.put("totalCount", totalCount);
		result.put("totalPageCount", totalPageCount);
		result.put("startPageNum", startPageNum);
		result.put("endPageNum", endPageNum);
		result.put("currentPage", currentPageNum);
		result.put("searchMem", searchMem);
		
		return result;
	}
	
	
	/*
	 * public List<AdminMemEntity> selectMemList(){ return admindao.selectMemList(
	 * memNick, startRounum,endRonum); }
	 */
	

//회원리스트
//	public List<AdminMemEntity> memList(){
//		return admindao.selectMemList();
//	}
	
	//등급변경 활성화 여부
	public Integer adminMemInfo(Map<String, Object> map) {
		return admindao.adminMemInfo(map);
	}
	
	//회원검색
	public Map<String, Object> search( int memNum, int memPageNum, int currentPageNum, String searchMem){
		
		//현재페이지: currentPage
		// 하단에 표시할 페이지 수: memPageNum
		//화면에 한번에 표시되는  글 수 : memNum	
		Map<String, Object> result = null;
		
		//총 게시글 개수
		//DB가서 그때그때 알아와야함 - 호텔 한개 당 리뷰글이 몇개냐에 따라 달라질 수 있음
		int totalCount = admindao.totalCountSearch(searchMem);
		
		int startRownum = memNum * (currentPageNum - 1) + 1;
		int endRownum = memNum * currentPageNum;
		
//				전체페이지수(총 게시글 개수/한 페이지 당 글 수) => (총 게시글 개수%한 페이지 당 글 수== 0)?(총 게시글 개수/한 페이지 당 글 수):(총 게시글 개수/한 페이지 당 글 수+1)
		int totalPageCount = (totalCount % memNum == 0) ? (totalCount / memNum) : (totalCount / memNum) + 1;
		// 조건문 - 앞에가 0이 맞으면 : 앞에꺼, 0이 아니면 : 뒤에꺼
		
		//시작페이지
		int startPageNum = (currentPageNum % memPageNum == 0) ? ((currentPageNum / memPageNum) - 1) * memPageNum + 1
				: (currentPageNum / memNum) * memNum + 1;
		
		//끝페이지
		int endPageNum = (startPageNum + memPageNum > totalPageCount) ? totalPageCount : startPageNum + memPageNum - 1;
		
		List<AdminMemEntity> memList = admindao.selectMemListSearch(startRownum, endRownum, searchMem);
		result = new HashMap<String, Object>();
		result.put("memList", memList);
		result.put("totalCount", totalCount);
		result.put("totalPageCount", totalPageCount);
		result.put("startPageNum", startPageNum);
		result.put("endPageNum", endPageNum);
		result.put("currentPage", currentPageNum);
		result.put("searchMem", searchMem);

		return result;
	}
	
	//게시글리스트
	public List<AdminBoardEntity> boardList(){
		return admindao.boardList();
	}
	
	//게시글 검색(select)
	public List<AdminBoardEntity> keywordsearch(Map<String, Object> map){
		return admindao.keywordsearch(map);
	}

	//좋아요 정렬
	public List<AdminBoardEntity> boardLikes() {
		
		return admindao.boardLike();
	}
	//조회수 정렬
	public List<AdminBoardEntity> boardView() {
			
		return admindao.boardView();
	}
		
	//신고게시글
	public List<AdminBoardEntity> complainList(){
		return admindao.complainList();
	}
	
	//신고게시글 검색
	public List<AdminBoardEntity> complainsearch(String memNick){
		return admindao.complainsearch(memNick);
	}
	
	//신고수 초기화
	public Integer complainReset(Integer diaryId) {
		return admindao.complainReset(diaryId);
	}
	
	//신고수 정렬
	public List<AdminBoardEntity> boardReport(){
		return admindao.boardReport();
	}
	
	// 결제 취소 페이지
	// 결제 목록
	public List<Map<String, Object>> payList(){
		return admindao.payList();
	}

	// 결제 취소
	public int payCancel(Map<String, Object> map) {
		return admindao.payCancel(map);
	}
	
	//결제취소 회원 검색
	public List<AdminStoreEntity> cancelSearch(String memNick){
		return admindao.cancelSearch(memNick);
	}
	
	// 상품 관리 페이지
	// 상품목록
	public List<Map<String, Object>> itemList(){
		return admindao.itemList();
	}
	// 상품정보
	public Map<String, Object> itemInfo(String itemCode){
		return admindao.itemInfo(itemCode);
	}
	// 상품추가
	public int itemInsert(Map<String, Object> map) {
		return admindao.itemInsert(map);
	}
	// 상품수정
	public int itemUpdate(Map<String, Object> map) {
		return admindao.itemUpdate(map);
	}
	//상품 삭제
	public int itemDelete(String itemCode) {
		return admindao.itemDelete(itemCode);
	}
	//상품검색
	public List<AdminStoreEntity> itemsearch(String itemCode){
		return admindao.itemsearch(itemCode);
	}
	
	//해당 호텔 리뷰 작성된거 불러오기
	
}
