package com.study.springboot.service;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.springboot.dao.AReservationDao;
import com.study.springboot.dto.ACustomerListDto;
import com.study.springboot.dto.AReservationDto;

@Service
public class AndroidReservationService implements IAndroidReservationService{
	@Autowired
	AReservationDao idao;
	//예약 시 로그인 아이디값으로 회원정보를 가져옴.
	public String getRsvMemInfo(String c_id) {
		System.out.println("Service : getRerservation Member Info");
		ArrayList<ACustomerListDto> list=new ArrayList<>();
		list=idao.getRsvMemInfoDao(c_id);
		
		JSONArray jsonArray1 = new JSONArray();

		JSONObject jb = new JSONObject();
		
		jb.put("c_index", list.get(0).getC_index());
		jb.put("c_name",list.get(0).getC_name());
		jb.put("c_phone",list.get(0).getC_phone());		
		jsonArray1.add(jb);
		System.out.println(jb.toString());
	
		return jb.toString();
	}
	//예약정보 저장하기
	public int insertReservationInfo(Map<String, Object> map) {
		System.out.println("Service : insert reservation info");
		
		int nResult=idao.insertRsvDao(map);
		System.out.println("service nresult: "+nResult);
		return nResult;		
	}
	//예약번호로 예약정보 가져오기
	public String getRsvInfo(String r_rsvnumber) {
		System.out.println("service: get reservation info by rsvnum");
		ArrayList<AReservationDto> list=new ArrayList<>();
		list=idao.getRsvInfoDao(r_rsvnumber);
		
		JSONArray jsonArray1 = new JSONArray();
		JSONObject jb = new JSONObject();
		
		jb.put("r_name", list.get(0).getR_name());
		jb.put("nickname",list.get(0).getNickname());
		jb.put("c_phone", list.get(0).getC_phone());
		jb.put("b_party", list.get(0).getB_party());
		if(!(list.get(0).getRequest()).equals(null)) {
			jb.put("request", list.get(0).getRequest());
		}
		jb.put("tdate", list.get(0).getTdate());
		jb.put("time", list.get(0).getTime());
	
		jsonArray1.add(jb);
		System.out.println(jb.toString());
		
		return jb.toString();
	}
	
}
