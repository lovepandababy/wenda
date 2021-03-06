package com.zm.controller;

import com.zm.model.Comment;
import com.zm.model.EntityType;
import com.zm.model.HostHolder;
import com.zm.service.CommentService;
import com.zm.service.LikeService;
import com.zm.utils.JedisAdopter;
import com.zm.utils.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Ellen on 2017/5/23.
 */
@Controller
public class LikeController {
	@Autowired
	private JedisAdopter redisUtils;
	@Autowired
	private HostHolder hostHolder;
	@Autowired
	private CommentService commentService;
	@Autowired
	private LikeService likeService;

	private static final Logger logger = LoggerFactory.getLogger(LikeController.class);

	@RequestMapping(path = { "/like" }, method = { RequestMethod.POST })
	@ResponseBody
	public String like(@RequestParam("commentId") int commentId) {
		if (hostHolder.getUser() == null) {
			return WendaUtil.getJSONString(999);
		}
		long likeCount = likeService.like(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,commentId);
		return WendaUtil.getJSONString(0,String.valueOf(likeCount));
	}

    @RequestMapping(path = { "/dislike" }, method = { RequestMethod.POST })
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId) {
        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString(999);
        }
        long likeCount = likeService.dislike(hostHolder.getUser().getId(),EntityType.ENTITY_COMMENT,commentId);
        return WendaUtil.getJSONString(0,String.valueOf(likeCount));
    }
}
