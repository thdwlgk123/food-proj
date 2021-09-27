package com.study.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.springboot.dao.AMembershipDao;
import com.study.springboot.dao.AReservationDao;
import com.study.springboot.dao.ARestaurantDao;
import com.study.springboot.dto.ACustomerListDto;
import com.study.springboot.dto.AGoodButtonDto;
import com.study.springboot.dto.AReservationDto;
import com.study.springboot.dto.ARestaurantListDto;
import com.study.springboot.dto.AReviewDto;
import com.study.springboot.dto.AReviewListDto;
import com.study.springboot.dto.ASirenDto;

@Service
public class AndroidRestaurantService implements IAndroidRestaurantService{
	@Autowired
	ARestaurantDao testdao;
	@Autowired
	AReservationDao insertdao;
	@Autowired
	AMembershipDao member;
	
	//인기맛집 리스트
	public String getlikeList() {
		System.out.println("Service:likelist data");
		
		String json = "";
		int i = 0;
		ArrayList<ARestaurantListDto> list = testdao.likeSelect();
			
		 JSONObject fjb = new JSONObject(); 
		 JSONArray jsonArray1 = new JSONArray();
		 for(i=0; i<list.size(); i++) {
			JSONObject jb = new JSONObject();
			
			jb.put("m_number",list.get(i).getM_number());
			jb.put("r_name",list.get(i).getR_name());
			jb.put("gu_name", list.get(i).getGu_name());
			jb.put("t_name", list.get(i).getT_name());
			jb.put("countreview",testdao.countReviewDao(list.get(i).getM_number(),list.get(i).getR_name()));
			
			List<String> gradelist=testdao.getReviewGrade(list.get(i).getM_number(), list.get(i).getR_name());
			System.out.print("gradelist= "+gradelist+" ,");
			if(gradelist.size()==0) {
				jb.put("gradeavg", "0");		
			}else {
				int gradesum=0;
				for(int j=0;j<gradelist.size();j++) {
					gradesum+=Integer.parseInt(gradelist.get(j));
				}
				String getavg=String.format("%.1f", gradesum/(double)gradelist.size());
				System.out.println("getavg : "+getavg);
				jb.put("gradeavg", getavg);		
			}
				
			jsonArray1.add(jb);
			fjb.put("likeboard",jsonArray1);			 
		}
		
		System.out.println(fjb.toString());
		
		return fjb.toString();
	}
	
	public String getrecentList() {
		System.out.println("Service:recentList");
		
		String json = "";
		int i = 0;
		ArrayList<ARestaurantListDto> list = testdao.timeorderSelect();
			
		 JSONObject fjb = new JSONObject(); 
		 JSONArray jsonArray1 = new JSONArray();
		 for(i=0; i<list.size(); i++) {
			JSONObject jb = new JSONObject();
			
			jb.put("m_number",list.get(i).getM_number());
			jb.put("r_name",list.get(i).getR_name());
			jb.put("gu_name", list.get(i).getGu_name());
			jb.put("t_name", list.get(i).getT_name());
			jb.put("countreview",testdao.countReviewDao(list.get(i).getM_number(),list.get(i).getR_name()));
			
			List<String> gradelist=testdao.getReviewGrade(list.get(i).getM_number(), list.get(i).getR_name());
			System.out.print("gradelist= "+gradelist+" ,");
			if(gradelist.size()==0) {
				jb.put("gradeavg", "0");		
			}else {
				int gradesum=0;
				for(int j=0;j<gradelist.size();j++) {
					gradesum+=Integer.parseInt(gradelist.get(j));
				}
				String getavg=String.format("%.1f", gradesum/(double)gradelist.size());
				System.out.println("getavg : "+getavg);
				jb.put("gradeavg", getavg);		
			}
			jsonArray1.add(jb);

			fjb.put("recentboard",jsonArray1);			 
		}
		
		System.out.println(fjb.toString());
		
		return fjb.toString();
	}
	
	//검색어 입력을 통한 리스트 출력
	public String getsearchList(String searchword) {
		System.out.println("Service:searchlist data, 검색어: "+searchword);
				
		JSONObject fjb = new JSONObject(); 
		String json = "";
		int i = 0;
		ArrayList<ARestaurantListDto> list = testdao.searchSelect(searchword);

		JSONArray jsonArray1 = new JSONArray();
		for(i=0; i<list.size(); i++) {
			JSONObject jb = new JSONObject();
			jb.put("m_number",list.get(i).getM_number());
			jb.put("r_name",list.get(i).getR_name());
			jb.put("gu_name", list.get(i).getGu_name());
			jb.put("t_name", list.get(i).getT_name());

			List<String> gradelist=testdao.getReviewGrade(list.get(i).getM_number(), list.get(i).getR_name());
			System.out.print("gradelist= "+gradelist+" ,");
			if(gradelist.size()==0) {
				jb.put("gradeavg", "0");		
			}else {
				int gradesum=0;
				for(int j=0;j<gradelist.size();j++) {
					gradesum+=Integer.parseInt(gradelist.get(j));
				}
				String getavg=String.format("%.1f", gradesum/(double)gradelist.size());
				System.out.println("getavg : "+getavg);
				jb.put("gradeavg", getavg);		
			}
			jsonArray1.add(jb);	 
//			System.out.println(jsonArray1);
			fjb.put("searchboard",jsonArray1);	
		}
		System.out.println(fjb.toString());
		return fjb.toString();
	}
	
	//검색어 입력을 통한 리스트 출력
		public String noCheckReservation(String r_rsvnumber) {
			System.out.println("Service:noCheckReservation");
					
			JSONObject fjb = new JSONObject(); 
			String json = "";
			int i = 0;
			i = testdao.noCheckReservation(r_rsvnumber);
			System.out.println(i);
			JSONArray jsonArray1 = new JSONArray();
			JSONObject jb = new JSONObject();
			jb.put("noCheck","3");

				jsonArray1.add(jb);	 
//				System.out.println(jsonArray1);
				fjb.put("noCheck",jsonArray1);	
			
			System.out.println(fjb.toString());
			return fjb.toString();
		}
		public String checkReservation(String r_rsvnumber) {
			System.out.println("Service:checkReservation");
					
			JSONObject fjb = new JSONObject(); 
			String json = "";
			int i = 0;
			i = testdao.checkReservation(r_rsvnumber);
			System.out.println(i);
			JSONArray jsonArray1 = new JSONArray();
			JSONObject jb = new JSONObject();
			jb.put("noCheck","1");

				jsonArray1.add(jb);	 
//				System.out.println(jsonArray1);
				fjb.put("Check",jsonArray1);	
			
			System.out.println(fjb.toString());
			return fjb.toString();
		}
	
	//해당 매장 메뉴목록 가져오기
	public String getMenuList(String mnumber, String storename) {
		System.out.println("Service: getmenulist");
		
		JSONObject fjb = new JSONObject(); 
		String json = "";
		int i = 0;
		
		String menulist=testdao.getMenuListDao(mnumber, storename);
		System.out.println("메뉴:"+menulist);
		
//		String[] menu=menulist.split(" ");
//		for(i=0;i<menu.length;i++) {
//			System.out.println(menu[i]);
//		}
		fjb.put("menulist", menulist);
		System.out.println("menulist jsonobject:" +fjb.toString());
		
		return fjb.toString();
		
	}
	
	//해당 매장 정보 가져오기
	public String getRestaurantInfo(String mnumber, String storename) {
		System.out.println("Service: getRestaurantInfo");
		
		JSONObject fjb = new JSONObject(); 
		String json = "";
		int i = 0;
		
		ArrayList<ARestaurantListDto> infolist=testdao.getrestaurantInfo(mnumber, storename);
		
		JSONArray jsonArray1 = new JSONArray();

			JSONObject jb = new JSONObject();
			jb.put("r_index", infolist.get(0).getR_index());
			jb.put("m_number",infolist.get(0).getM_number());
			jb.put("r_name",infolist.get(0).getR_name());
			jb.put("gu_name", infolist.get(0).getGu_name());
			jb.put("s_name", infolist.get(0).getS_name());
			jb.put("t_name", infolist.get(0).getT_name());
			jb.put("h_number", infolist.get(0).getH_number());
			jb.put("h_name", infolist.get(0).getH_name());
			jb.put("coordinates_x", infolist.get(0).getCoordinates_x());
			jb.put("coordinates_y", infolist.get(0).getCoordinates_y());
			jb.put("r_number", infolist.get(0).getR_number());
			jb.put("r_adress2", infolist.get(0).getR_adress2());
			jb.put("r_adress1", infolist.get(0).getR_adress1());
			jb.put("r_menu", infolist.get(0).getR_menu());
			jb.put("ㅊ", infolist.get(0).getR_image());
			jb.put("tdate", infolist.get(0).getTdate());
			
			jsonArray1.add(jb);	 
//			System.out.println(jsonArray1);
			fjb.put("infolist",jsonArray1);	

		System.out.println(fjb.toString());
		System.out.println("infolist jsonobject:" +fjb.toString());
		
		return fjb.toString();
		
	}
	//좋아요 확인
	public int checkLike(String m_number, String r_name, String c_id) {
		System.out.println("Service: checklike");
		int nResult=testdao.checkLikeDao(c_id, r_name, m_number);
		System.out.println("service count checklike: "+nResult);
		return nResult;			
	}
	//좋아요
	public int doLike(String m_number, String r_name, String c_id) {
		System.out.println("Service : dolike"+m_number+","+r_name+","+c_id);
		int nResult=testdao.doLikeDao(c_id, r_name, m_number);
		System.out.println("service dolike insert nResult: "+nResult);
		return nResult;
	}
	//좋아요취소
	public int undoLike(String m_number, String r_name, String c_id) {
		System.out.println("Service : undolike");
		int nResult=testdao.undoLikeDao(c_id, r_name, m_number);
		System.out.println("service undolike delete nResult: "+nResult);
		return nResult;
	}
	//아이디별 좋아요 리스트
	public String getLikeList(String c_id) {
		System.out.println("Service: get LIKE List");
		ArrayList<AGoodButtonDto> list1=testdao.getLikeListDao(c_id);
		System.out.println("cid likelist result: "+list1);
		
		int i=0;
		ArrayList<ARestaurantListDto> list2=new ArrayList<>();
		
		for(i=0;i<list1.size();i++) {
			String m_number=list1.get(i).getM_number();
			String r_name=list1.get(i).getR_name();
			list2.add(testdao.selectLikeListDao(m_number, r_name));
		}
		System.out.println("list2 result: "+list2);
		
		JSONObject fjb = new JSONObject(); 
		String json = "";
		JSONArray jsonArray1 = new JSONArray();
		for(i=0; i<list2.size(); i++) {
			JSONObject jb = new JSONObject();
			jb.put("m_number",list2.get(i).getM_number());
			jb.put("r_name",list2.get(i).getR_name());
			jb.put("gu_name", list2.get(i).getGu_name());
			jb.put("t_name", list2.get(i).getT_name());
			jsonArray1.add(jb);	 
//			System.out.println(jsonArray1);
			fjb.put("likelistboard",jsonArray1);	
		}
		System.out.println(fjb.toString());
		return fjb.toString();
		
	}
	
//	====================================주말지하
	public String getReviewList(String m_number, String r_name) {
		System.out.println("Service: getReviewList");
		System.out.println("Servcie request check : "+m_number+", "+r_name);
		
		int i=0;
		ArrayList<AReviewDto> list=testdao.getReviewListDao(m_number, r_name);
		String json = "";
		JSONArray jsonArray1 = new JSONArray();
		for(i=0;i<list.size();i++) {
			JSONObject jb = new JSONObject();
			jb.put("nickname", list.get(i).getNickname());
			jb.put("grade", list.get(i).getGrade());
			jb.put("contents", list.get(i).getContents());
			jb.put("filename", list.get(i).getFilename());
			jsonArray1.add(jb);
		}
		System.out.println(jsonArray1.toString());
		return jsonArray1.toString();
	}

//	=======================================================호범
	// 로그인 및 본인확인
		public int getLoginResult(Map<String, String> map) {
			int nResult = member.getLoginResult(map);
			return nResult;
		}

		// 회원가입
		public int getJoinResult(Map<String, String> map) {
			int nResult = member.getJoinResult(map);
			return nResult;
		}

		// 회원 아이디를 통해 내 예약내역 출력
		public String getCustomerResList(String c_id) {
			System.out.println("Service:Customer Reservation Data, 검색어: " + c_id);

			JSONObject fjb = new JSONObject();
			int i = 0;
			ArrayList<AReservationDto> list = member.getCustomerResList(c_id);

			JSONArray jsonArray1 = new JSONArray();
			for (i = 0; i < list.size(); i++) {
				JSONObject jb = new JSONObject();
				jb.put("r_index", list.get(i).getR_index());
				jb.put("r_rsvnumber", list.get(i).getR_rsvnumber());
				jb.put("m_number", list.get(i).getM_number());
				jb.put("r_name", list.get(i).getR_name());
				jb.put("c_id", list.get(i).getC_id());
				jb.put("nickname", list.get(i).getNickname());
				jb.put("c_phone", list.get(i).getC_phone());
				jb.put("b_party", list.get(i).getB_party());
				jb.put("condition_check", list.get(i).getCondition_check());
				jb.put("reject", list.get(i).getReject());
				jb.put("res_payment", list.get(i).getRes_payment());
				jb.put("request", list.get(i).getRequest());
				jb.put("tdate", list.get(i).getTdate());
				jb.put("time", list.get(i).getTime());
				jsonArray1.add(jb);
//						System.out.println(jsonArray1);
				fjb.put("myreservation", jsonArray1);
			}
			System.out.println(fjb.toString());
			return fjb.toString();
		}
		
		// 회원 아이디를 통해 업주의 예약리스트 출력
				public String getReservationList(String c_id) {
					System.out.println("Service:getResercationList"); 
					List<ACustomerListDto> list2 = member.searchR_name(c_id);
					String r_name = list2.get(0).getR_name();
					System.out.println("r_name : " + r_name);
					JSONObject fjb = new JSONObject();
					int i = 0;
					ArrayList<AReservationDto> list = member.getReservationList(r_name);

					JSONArray jsonArray1 = new JSONArray();
					for (i = 0; i < list.size(); i++) {
						JSONObject jb = new JSONObject();
						jb.put("r_index", list.get(i).getR_index());
						jb.put("r_rsvnumber", list.get(i).getR_rsvnumber());
						jb.put("m_number", list.get(i).getM_number());
						jb.put("r_name", list.get(i).getR_name());
						jb.put("c_id", list.get(i).getC_id());
						jb.put("nickname", list.get(i).getNickname());
						jb.put("c_phone", list.get(i).getC_phone());
						jb.put("b_party", list.get(i).getB_party());
						jb.put("condition_check", list.get(i).getCondition_check());
						jb.put("reject", list.get(i).getReject());
						jb.put("res_payment", list.get(i).getRes_payment());
						jb.put("request", list.get(i).getRequest());
						jb.put("tdate", list.get(i).getTdate());
						jb.put("time", list.get(i).getTime());
						jsonArray1.add(jb);
//								System.out.println(jsonArray1);
						fjb.put("myreservation", jsonArray1);
					}
					System.out.println(fjb.toString());
					return fjb.toString();
				}

		// 회원 아이디를 통해 내 예약내역 출력
		public String getMyInfo(String c_id) {
			System.out.println("Service:Customer Myinfo Data, 검색어: " + c_id);

			JSONObject fjb = new JSONObject();
			int i = 0;
			ArrayList<ACustomerListDto> list = member.getMyInfo(c_id);

			JSONArray jsonArray1 = new JSONArray();
			for (i = 0; i < list.size(); i++) {
				JSONObject jb = new JSONObject();
				jb.put("c_name", list.get(i).getC_name());
				jb.put("c_id", list.get(i).getC_id());
				jb.put("c_phone", list.get(i).getC_phone());
				jb.put("c_email", list.get(i).getC_eMail());
				jb.put("nickname", list.get(i).getNickname());
				jb.put("tdate", list.get(i).getTdata());
				jsonArray1.add(jb);
//								System.out.println(jsonArray1);
				fjb.put("myinfo", jsonArray1);
			}
			System.out.println(fjb.toString());
			return fjb.toString();
		}

		// 내 정보 불러오기(닉네임, 프로필사진)
		public String getMyProfile(String c_id) {
			System.out.println("Service:Customer MyProfile Data, 검색어: " + c_id);

			JSONObject fjb = new JSONObject();
			int i = 0;
			ArrayList<ACustomerListDto> list = member.getMyProfile(c_id);

			JSONArray jsonArray1 = new JSONArray();
			for (i = 0; i < list.size(); i++) {
				JSONObject jb = new JSONObject();
				jb.put("nickname", list.get(i).getNickname());
				jb.put("userprofile", list.get(i).getUserprofile());
				jsonArray1.add(jb);
//								System.out.println(jsonArray1);
				fjb.put("myprofile", jsonArray1);
			}
			System.out.println(fjb.toString());
			return fjb.toString();
		}

		// 내 정보수정 DATA 불러오기
		public String getModifyData(String c_id) {
			System.out.println("Service:Customer modify Data: " + c_id);

			JSONObject fjb = new JSONObject();
			int i = 0;
			ArrayList<ACustomerListDto> list = member.getMyProfile(c_id);

			JSONArray jsonArray1 = new JSONArray();
			for (i = 0; i < list.size(); i++) {
				JSONObject jb = new JSONObject();
				jb.put("c_name", list.get(i).getC_name());
				jb.put("c_id", list.get(i).getC_id());
				jb.put("c_phone", list.get(i).getC_phone());
				jb.put("c_email", list.get(i).getC_eMail());
				jsonArray1.add(jb);
//								System.out.println(jsonArray1);
				fjb.put("mod_data", jsonArray1);
			}
			System.out.println(fjb.toString());
			return fjb.toString();
		}
		
		// 내 정보 수정(Update)
		public int setUpdateMyProfile(Map<String, String> map) {
			int nResult = member.setUpdateMyProfile(map);
			return nResult;
		}
		
		// 내 리뷰 불러오기
		public String getMyReview(String c_id) {
			System.out.println("Service:MyReview Data: " + c_id);
			
			JSONObject fjb = new JSONObject();
			int i = 0;
			ArrayList<AReviewListDto> list = member.getMyReview(c_id);

			JSONArray jsonArray1 = new JSONArray();
			for (i = 0; i < list.size(); i++) {
				JSONObject jb = new JSONObject();
				jb.put("r_name", list.get(i).getR_name());
				jb.put("grade", list.get(i).getGrade());
				jb.put("filename", list.get(i).getFilename());
				jb.put("contents", list.get(i).getContents());
				jb.put("tdate", list.get(i).getTdate());
				jsonArray1.add(jb);
//								System.out.println(jsonArray1);
				fjb.put("myReviewList", jsonArray1);
			}
			
			System.out.println(fjb.toString());
			return fjb.toString();
			
		}

		// 점주 가게 정보조회
		public String getRestaurantInfoMg(String m_id) {
			System.out.println("Service: getRestaurantInfo Manager");
			
			JSONObject fjb = new JSONObject();
			
			ArrayList<ARestaurantListDto> infolist=member.getrestaurantInfoMg(m_id);
			
			JSONArray jsonArray1 = new JSONArray();

				JSONObject jb = new JSONObject();
				jb.put("r_index", infolist.get(0).getR_index());
				jb.put("m_number",infolist.get(0).getM_number());
				jb.put("r_name",infolist.get(0).getR_name());
				jb.put("gu_name", infolist.get(0).getGu_name());
				jb.put("s_name", infolist.get(0).getS_name());
				jb.put("t_name", infolist.get(0).getT_name());
				jb.put("h_number", infolist.get(0).getH_number());
				jb.put("h_name", infolist.get(0).getH_name());
				jb.put("coordinates_x", infolist.get(0).getCoordinates_x());
				jb.put("coordinates_y", infolist.get(0).getCoordinates_y());
				jb.put("r_number", infolist.get(0).getR_number());
				jb.put("r_adress2", infolist.get(0).getR_adress2());
				jb.put("r_adress1", infolist.get(0).getR_adress1());
				jb.put("r_menu", infolist.get(0).getR_menu());
				jb.put("ㅊ", infolist.get(0).getR_image());
				jb.put("tdate", infolist.get(0).getTdate());
				
				jsonArray1.add(jb);	 
//				System.out.println(jsonArray1);
				fjb.put("infolist_mg",jsonArray1);	

			System.out.println(fjb.toString());
			System.out.println("infolist_mg jsonobject:" +fjb.toString());
			
			return fjb.toString();
			
		}
		
		// 가게 정보수정
		public int setStoreUpdate(Map<String, String> map) {
			int nResult = member.setStoreUpdate(map);
			return nResult;
		}

		public int setUpdateNickname(String nickname, String c_id) {
			int nResult = member.setUpdateNickname(nickname, c_id);
			return nResult;
		}
		
		public String getCustomerSiren(String c_id) {
							System.out.println("Service:Customer Siren Data, 검색어: " + c_id);

							JSONObject fjb = new JSONObject();
							int i = 0;
							ArrayList<ASirenDto> list = member.getCustomersirenList(c_id);

							JSONArray jsonArray1 = new JSONArray();
							for (i = 0; i < list.size(); i++) {
								JSONObject jb = new JSONObject();
								jb.put("r_index", list.get(i).getR_index());
								jb.put("r_rsvnumber", list.get(i).getR_rsvnumber());
								jb.put("m_number", list.get(i).getM_number());
								jb.put("r_name", list.get(i).getR_name());
								jb.put("c_id", list.get(i).getC_id());
								jb.put("r_menu", list.get(i).getR_menu());
								jb.put("r_menu", list.get(i).getC_name());
								jb.put("c_phone", list.get(i).getC_phone());
								
								jb.put("condition_check", list.get(i).getCondition_check());
								jb.put("reject", list.get(i).getReject());
								jb.put("res_payment", list.get(i).getRes_payment());
								jb.put("request", list.get(i).getRequest());
								jb.put("tdate", list.get(i).getTdate());
							
								jsonArray1.add(jb);
//										System.out.println(jsonArray1);
								fjb.put("siren", jsonArray1);
							}
							System.out.println(fjb.toString());
							return fjb.toString();
						}

		@Override
		public int andCheckId(Map<String, String> map) {
			int nResult = member.andCheckId(map);
			return nResult;
		}
		@Override
		public int andCheckNickName(Map<String, String> map) {
			int nResult = member.andCheckNickName(map);
			return nResult;
		}

	
	
	
}
