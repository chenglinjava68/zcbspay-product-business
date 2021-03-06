package com.zcbspay.platform.business.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcbspay.platform.business.commons.utils.BeanCopyUtil;
import com.zcbspay.platform.business.order.bean.BatchResultBean;
import com.zcbspay.platform.business.order.bean.FileContentBean;
import com.zcbspay.platform.business.order.bean.OrderResultBean;
import com.zcbspay.platform.business.order.bean.ResultBean;
import com.zcbspay.platform.business.order.service.OrderQueryService;

@Service("orderQueryService")
@Transactional
public class OrderQueryServiceImpl implements OrderQueryService {
	private static final Logger logger = LoggerFactory.getLogger(OrderQueryServiceImpl.class);
	@Autowired
	private com.zcbspay.platform.support.order.query.query.service.QueryOrderService queryOrderService;

	@Override
	public ResultBean queryOrder(String merchNo, String orderId) {
		OrderResultBean orderResultBean;
		try {
			orderResultBean = BeanCopyUtil.copyBean(OrderResultBean.class,
					queryOrderService.queryOrder(merchNo, orderId));
		} catch (com.zcbspay.platform.support.order.query.exception.QueryOrderException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return new ResultBean(e.getCode(),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("订单服务异常");
			return new ResultBean("PC013", "订单服务异常!");
		}
		return new ResultBean(orderResultBean);
	}

	@Override
	public ResultBean queryOrderByTN(String tn) {
		OrderResultBean orderResultBean;

		try {
			orderResultBean = BeanCopyUtil.copyBean(OrderResultBean.class, queryOrderService.queryOrderByTN(tn));
		} catch (com.zcbspay.platform.support.order.query.exception.QueryOrderException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return new ResultBean(e.getCode(),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("订单服务异常");
			return new ResultBean("PC013", "订单服务异常!");
		}
		return new ResultBean(orderResultBean);
	}

	@Override
	public ResultBean queryInsteadPayOrder(String merchNo, String orderId) {
		OrderResultBean orderResultBean;
		try {
			orderResultBean = BeanCopyUtil.copyBean(OrderResultBean.class,
					queryOrderService.queryInsteadPayOrder(merchNo, orderId));
		} catch (com.zcbspay.platform.support.order.query.exception.QueryOrderException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return new ResultBean(e.getCode(),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("订单服务异常");
			return new ResultBean("PC013", "订单服务异常!");
		}
		return new ResultBean(orderResultBean);
	}

	@Override
	public ResultBean queryConcentrateCollectionOrder(String tn) {
		OrderResultBean orderResultBean;
		try {
			orderResultBean = BeanCopyUtil.copyBean(OrderResultBean.class,
					queryOrderService.queryConcentrateCollectionOrder(tn));
		} catch (com.zcbspay.platform.support.order.query.exception.QueryOrderException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return new ResultBean(e.getCode(),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询实时代收订单服务异常");
			return new ResultBean("PC013", "订单服务异常!");
		}
		return new ResultBean(orderResultBean);
	}

	@Override
	public ResultBean queryConcentratePaymentOrder(String tn) {
		OrderResultBean orderResultBean;
		try {
			orderResultBean = BeanCopyUtil.copyBean(OrderResultBean.class,
					queryOrderService.queryConcentratePaymentOrder(tn));
		} catch (com.zcbspay.platform.support.order.query.exception.QueryOrderException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return new ResultBean(e.getCode(),e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询实时代付订单服务异常");
			return new ResultBean("PC013", "订单服务异常!");
		}
		return new ResultBean(orderResultBean);
	}

	@Override
	public ResultBean queryConcentrateCollectionBatch(String merchNo, String batchNo, String txnDate) {
		BatchResultBean batchResultBean;
		com.zcbspay.platform.support.order.query.query.bean.BatchResultBean sourceBean;
		List<FileContentBean> fileContentList = new ArrayList<>();
		try {
			// 获取查询结果
			sourceBean = queryOrderService.queryConcentrateCollectionBatch(merchNo, batchNo, txnDate);
			// 复制属性值
			batchResultBean = BeanCopyUtil.copyBean(BatchResultBean.class, sourceBean);

			// BatchResultBean中的List<FileContentBean>进行复制
			for (com.zcbspay.platform.support.order.query.query.bean.FileContentBean fileContentBean : sourceBean
					.getFileContentList()) {
				fileContentList.add(BeanCopyUtil.copyBean(FileContentBean.class, fileContentBean));
			}
			// 文件域赋值
			batchResultBean.setFileContentList(fileContentList);
			return new ResultBean(batchResultBean);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询批量代收订单服务异常");
			return new ResultBean("PC013", "订单服务异常!");
		}
	}

	@Override
	public ResultBean queryConcentratePaymentBatch(String merchNo, String batchNo, String txnDate) {
		BatchResultBean batchResultBean;
		com.zcbspay.platform.support.order.query.query.bean.BatchResultBean sourceBean;
		List<FileContentBean> fileContentList = new ArrayList<>();
		try {
			// 获取查询结果
			sourceBean = queryOrderService.queryConcentratePaymentBatch(merchNo, batchNo, txnDate);
			// 复制属性值
			batchResultBean = BeanCopyUtil.copyBean(BatchResultBean.class, sourceBean);

			// BatchResultBean中的List<FileContentBean>进行复制
			for (com.zcbspay.platform.support.order.query.query.bean.FileContentBean fileContentBean : sourceBean
					.getFileContentList()) {
				fileContentList.add(BeanCopyUtil.copyBean(FileContentBean.class, fileContentBean));
			}
			// 文件域赋值
			batchResultBean.setFileContentList(fileContentList);
			return new ResultBean(batchResultBean);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询批量代付订单服务异常");
			return new ResultBean("BP0001", "订单查询异常!");
		}
	}

}
