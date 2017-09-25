package com.museum.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.framework.mybatis.model.QueryModel;
import com.museum.BaseMybatisTest;
import com.museum.model.AppraisalExpertIdea;
import com.museum.model.Appraisalinfo;

public class AppraisalinfoServiceTest extends BaseMybatisTest {

	@Autowired
	private AppraisalinfoService appraisalinfoServiceImpl;

	@Autowired
	private AppraisalExpertIdeaService appraisalExpertIdeaServiceImpl;

	Appraisalinfo record;
	List<AppraisalExpertIdea> expertIdeas;

	@Before
	public void setUp() throws Exception {

		//
		String appId = "54b2e6ddae4042b89f96c5844cacb154";
		record = this.appraisalinfoServiceImpl.findObjectById(appId);

		QueryModel queryModel = new QueryModel();
		queryModel.createCriteria().andEqualTo("dataid", appId);
		expertIdeas = this.appraisalExpertIdeaServiceImpl
				.findObjects(queryModel);

		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	String reson = "新的鉴定原因";
	String culName = "鉴定后名称";

	@Test
	@Rollback(value = false)
	public void testSaveAppraisalinfoListOfAppraisalExpertIdeaString()
			throws Exception {

		String delIds = "";

		Appraisalinfo recordNew = (Appraisalinfo) record.clone();
		recordNew.setApplicationreasons(reson);
		recordNew.setApprA0102(culName);

		ExecutorService es = Executors.newCachedThreadPool();

		for (int i = 0; i < 10; i++) {
			final int index = i;
			es.execute(new Runnable() {

				@Override
				public void run() {

					Appraisalinfo recordNew;
					try {
						recordNew = (Appraisalinfo) record.clone();
						recordNew.setApplicationreasons(reson + "_" + index);
						recordNew.setApprA0102(culName + "_" + index);

						// 生成新的对象
						List<AppraisalExpertIdea> expertIdeasNew = new ArrayList<AppraisalExpertIdea>();
						// BeanUtils.copyProperties(expertIdeas,
						// expertIdeasNew);

						int ii = 0;
						for (AppraisalExpertIdea expertIdea : expertIdeas) {
							expertIdea.setApprexpertOpinion(reson + ii + "--"
									+ index);
							ii++;
							expertIdeasNew.add(expertIdea);
						}
						appraisalinfoServiceImpl.save(recordNew,
								expertIdeasNew, "");
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		}

	}

	@Test
	@Rollback(value = false)
	public void testSaveAppraisalinfoListOfAppraisalExpertIdeaString1()
			throws Exception {
		String delIds = "";

		Appraisalinfo recordNew = (Appraisalinfo) record.clone();
		recordNew.setApplicationreasons(reson);
		recordNew.setApprA0102(culName + "_" + "测试内容中枯井 工枯井 基本原则顶替 ");
		recordNew.setApprType("sldfjsldfjsdfskdlfjasd);fasdfkasdf");
		// 生成新的对象
		List<AppraisalExpertIdea> expertIdeasNew = new ArrayList<AppraisalExpertIdea>();
		int ii = 0;
		for (AppraisalExpertIdea expertIdea : expertIdeas) {
			expertIdea.setApprexpertOpinion(reson + ii);
			ii++;
			expertIdeasNew.add(expertIdea);
		}

		appraisalinfoServiceImpl.save(recordNew, expertIdeasNew, "");

	}
}
