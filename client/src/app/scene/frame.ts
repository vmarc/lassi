export class Frame {
  dmxValues: any;

  constructor(readonly id: number,
              readonly name: string,
              dmxValues: Array<number>) {
    this.dmxValues = dmxValues;
  }

  public static empty(): Frame {
    const dmxValues = new Array<number>();
    for (let i = 0; i < 512; i++) {
      dmxValues[i] = 0;
    }
    return new Frame(-1, '', dmxValues);
  }

  public static fromJSON(jsonObject): Frame {
    if (!jsonObject) {
      return undefined;
    }
    return new Frame(
      jsonObject.id,
      jsonObject.name,
      jsonObject.dmxValues
    );
  }

}
