export class Scene {

  constructor(readonly id: number,
              readonly name: string,
              readonly dmxValues: Array<number>) {
  }

  public static empty(): Scene {
    const dmxValues = new Array<number>();
    for (let i = 0; i < 512; i++) {
      dmxValues[i] = 0;
    }
    return new Scene(-1, '', dmxValues);
  }

  public static fromJSON(jsonObject): Scene {
    if (!jsonObject) {
      return undefined;
    }
    return new Scene(
      jsonObject.id,
      jsonObject.name,
      jsonObject.dmxValues
    );
  }

}
