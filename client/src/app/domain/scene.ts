import { Frame } from './frame';

export class Scene {

  id: string;
  name: string;
  duration: number;
  buttonId: number;
  fadeTime: number;
  createdOn: Date;
  frames: Array<Frame>;

  constructor(id: string,
              name: string,
              duration: number,
              buttonId: number,
              fadeTime: number,
              createdOn: Date,
              frames?: Array<Frame>) {
    this.id = id;
    this.name = name;
    this.duration = duration;
    this.buttonId = buttonId;
    this.fadeTime = fadeTime;
    this.createdOn = createdOn;
    this.frames = frames;
  }

  public static fromJSON(jsonObject): Scene {
    if (!jsonObject) {
      return undefined;
    }
    return new Scene(
      jsonObject.id,
      jsonObject.name,
      jsonObject.duration,
      jsonObject.buttonId,
      jsonObject.fadeTime,
      jsonObject.createdOn,
      jsonObject.scenes
    );
  }
}
