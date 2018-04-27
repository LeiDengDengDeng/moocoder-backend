package com.moekr.aes.logic.service.impl;

import com.moekr.aes.data.dao.CommitDAO;
import com.moekr.aes.data.entity.Commit;
import com.moekr.aes.logic.service.CommitService;
import com.moekr.aes.logic.vo.CommitVO;
import com.moekr.aes.util.exceptions.AccessDeniedException;
import com.moekr.aes.util.exceptions.Asserts;
import com.moekr.aes.util.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommitServiceImpl implements CommitService {
	private final CommitDAO commitDAO;

	@Autowired
	public CommitServiceImpl(CommitDAO commitDAO) {
		this.commitDAO = commitDAO;
	}

	@Override
	public CommitVO retrieve(int userId, int commitId) throws ServiceException {
		Commit commit = commitDAO.findById(commitId);
		Asserts.notNull(commit, "所选的提交记录不存在");
		if (commit.getResult().getOwner().getId() == userId) {
			return new CommitVO(commit);
		} else if (commit.getResult().getExam().getCreator().getId() == commitId) {
			return new CommitVO(commit);
		}
		throw new AccessDeniedException();
	}

	@Override
	public CommitVO retrieve(int commitId) throws ServiceException {
		Commit commit = commitDAO.findById(commitId);
		Asserts.notNull(commit, "所选的提交记录不存在");
		return new CommitVO(commit);
	}
}