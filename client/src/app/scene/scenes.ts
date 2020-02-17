import { Scene } from './scene';

export class Scenes {

  constructor(name: string, scenes: Array<Scene>) {
  }


  public static fromJSON(jsonObject): Scenes {
    if (!jsonObject) {
      return undefined;
    }
    return new Scenes(
      jsonObject.name,
      jsonObject.scenes
    );
  }
}
