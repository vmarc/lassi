package lighting.server.frame;

import lighting.server.scene.Reply;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class FrameController {

    private final IFrameService frameService;

    public FrameController(IFrameService frameService) {
        this.frameService = frameService;
    }

    @GetMapping(value = "/api/recordframe/{frameId}")
    public Reply recordFrame(@PathVariable UUID frameId) {
        return frameService.recordFrame(frameId);
    }

    @GetMapping(value = "/api/gotoframe/{frameId}")
    public Reply gotoFrame(@PathVariable UUID frameId) { return frameService.gotoFrame(frameId);}

    @GetMapping(value = "/api/frame/{frameId}")
    public Frame frame(@PathVariable UUID frameId) {
        return frameService.getFrame(frameId);
    }

}
