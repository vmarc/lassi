package lighting.server.frame;

import lighting.server.scene.Reply;
import java.util.UUID;

public interface IFrameService {

    Reply recordFrame(UUID frameId);

    Reply gotoFrame(UUID frameId);

    Frame getFrame(UUID frameId);

}
