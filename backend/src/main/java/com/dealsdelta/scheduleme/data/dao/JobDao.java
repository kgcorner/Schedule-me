package com.dealsdelta.scheduleme.data.dao;


import com.dealsdelta.scheduleme.data.models.JobModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 23/08/22
 */

@Transactional
@Repository
public class JobDao extends GenericDao<JobModel> {
}