package kr.kakaruto.service;

import java.util.ArrayList;


import kr.kakaruto.dao.MemoDao;
import kr.kakaruto.domain.Memo;

public class MemoService {
	private MemoDao memoDao;
	
	public MemoService() {
		this.memoDao = new MemoDao();
	}
	
	/**
	 * 메모 목록 조회 Service
	 * @param pMemo
	 * @return
	 */
	public ArrayList<Memo> getMemoList(Memo pMemo){
		return memoDao.getMemoList(pMemo);
	}
	
	/**
	 * 메모 상세보기 Service
	 * @param pMemo
	 * @return
	 */
	public Memo getOneMemo(Memo pMemo){
		return memoDao.getOneMemo(pMemo);
	}
	
	/**
	 * 메모 등록 Service
	 * @param pMemo
	 */
	public void insertMemo(Memo pMemo){
		memoDao.insertMemo(pMemo);
	}
	
	/**
	 * 메모 수정 Service
	 * @param pMemo
	 */
	public void updateMemo(Memo pMemo){
		memoDao.updateMemo(pMemo);
	}
	
	/**
	 * 메모 삭제 Service
	 * @param pMemo
	 */
	public void deleteMemo(Memo pMemo){
		memoDao.deleteMemo(pMemo);
	}
}
