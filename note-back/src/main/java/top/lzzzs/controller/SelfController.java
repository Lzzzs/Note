package top.lzzzs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.lzzzs.common.R;
import top.lzzzs.service.SelfService;

@RestController
@RequestMapping("/self")
public class SelfController {
    @Autowired
    SelfService selfService;

    @GetMapping("/getGradeInfo")
    public R getGradeInfo(@RequestParam("userId") String userId) {
        return selfService.getGradeInfo(userId);
    }

    @GetMapping("/getPointInfo")
    public R getPointInfo(@RequestParam("userId") String userId) {
        return selfService.getPointInfo(userId);
    }

    // 兑换奖品
    @GetMapping("/exchangeAward")
    public R exchangeAward(@RequestParam("userId") String userId, @RequestParam("awardPoint") int awardPoint) {
        return selfService.exchangeAward(userId, awardPoint);
    }

    @GetMapping("/getUserAllNotesByUserId")
    public R getUserAllNotesByUserId(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("userId") String userId) {
        return selfService.getUserAllNotesByUserId(page, size, userId);
    }

    @GetMapping("/getUserAllCollectNotesByUserId")
    public R getUserAllCollectNotesByUserId(@RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam("userId") String userId) {
        return selfService.getUserAllCollectNotesByUserId(page, size, userId);
    }

}
