import { Scene } from './scene';

export class Scenes {

  constructor(id: number, name: string, duration: number, buttonID: number, createdOn: Date, scenes: Array<Scene>) {
  }


  public static fromJSON(jsonObject): Scenes {
    if (!jsonObject) {
      return undefined;
    }
    return new Scenes(
      jsonObject.id,
      jsonObject.name,
      jsonObject.duration,
      jsonObject.buttonID,
      jsonObject.createdOn,
      jsonObject.scenes
    );
  }
}
