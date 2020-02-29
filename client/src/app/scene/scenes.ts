import { Scene } from './scene';
import { v4 as uuidv4 } from 'uuid';

export class Scenes {

  constructor(id: uuidv4, name: string, duration: number, buttonId: number, createdOn: Date, scenes: Array<Scene>) {
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
