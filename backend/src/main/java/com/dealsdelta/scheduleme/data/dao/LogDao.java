package com.dealsdelta.scheduleme.data.dao;


import com.dealsdelta.scheduleme.data.models.LogModel;
import com.dealsdelta.scheduleme.data.repo.JobRepo;
import com.dealsdelta.scheduleme.data.repo.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 25/06/22
 */

@Repository
@Transactional
public class LogDao extends GenericDao<LogModel>{
}