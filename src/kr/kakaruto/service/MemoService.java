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
	 * �޸� ��� ��ȸ Service
	 * @param pMemo
	 * @return
	 */
	public ArrayList<Memo> getMemoList(Memo pMemo){
		return memoDao.getMemoList(pMemo);
	}
	
	/**
	 * �޸� �󼼺��� Service
	 * @param pMemo
	 * @return
	 */
	public Memo getOneMemo(Memo pMemo){
		return memoDao.getOneMemo(pMemo);
	}
	
	/**
	 * �޸� ��� Service
	 * @param pMemo
	 */
	public void insertMemo(Memo pMemo){
		memoDao.insertMemo(pMemo);
	}
	
	/**
	 * �޸� ���� Service
	 * @param pMemo
	 */
	public void updateMemo(Memo pMemo){
		memoDao.updateMemo(pMemo);
	}
	
	/**
	 * �޸� ���� Service
	 * @param pMemo
	 */
	public void deleteMemo(Memo pMemo){
		memoDao.deleteMemo(pMemo);
	}
}
