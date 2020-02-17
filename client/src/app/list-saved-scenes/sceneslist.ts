import { Scenes } from '../scene/scenes'

export class Sceneslist {

  constructor(scenes: Array<Scenes>) {
  }

  public static fromJSON(jsonObject): Sceneslist {
    if (!jsonObject) {
      return undefined;
    }
    return new Sceneslist(
      jsonObject.scenes
    );
  }

}
