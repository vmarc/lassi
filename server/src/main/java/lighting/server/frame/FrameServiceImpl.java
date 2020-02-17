package lighting.server.frame;

import lighting.server.scene.Reply;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class FrameServiceImpl implements IFrameService {

    @Override
    public Reply recordFrame(UUID frameId) {
        return null;
    }

    @Override
    public Reply gotoFrame(UUID frameId) {
        return null;
    }

    @Override
    public Frame getFrame(UUID frameId) {
        return null;
    }
}
