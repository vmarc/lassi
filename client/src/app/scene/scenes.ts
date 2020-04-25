import { Frame } from './frame';

export class Scenes {

  id: string;
  name: string;
  duration: number;
  buttonId: number;
  createdOn: Date;
  frames: Array<Frame>;

  constructor(id: string, name: string, duration: number, buttonId: number, createdOn: Date, frames?: Array<Frame>) {
    this.id = id;
    this.name = name;
    this.duration = duration;
    this.buttonId = buttonId;
    this.createdOn = createdOn;
    this.frames = frames;
  }


  public static fromJSON(jsonObject): Scenes {
    if (!jsonObject) {
      return undefined;
    }
    return new Scenes(
      jsonObject.id,
      jsonObject.name,
      jsonObject.duration,
      jsonObject.buttonId,
      jsonObject.createdOn,
      jsonObject.scenes
    );
  }
}
