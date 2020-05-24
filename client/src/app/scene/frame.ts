export class Frame {
  dmxValues: any;

  constructor(readonly id: number,
              readonly name: string,
              readonly universe: number,
              readonly createdOn: Date,
              dmxValues: Array<number>) {
    this.dmxValues = dmxValues;
  }

  public static empty(): Frame {
    const dmxValues = new Array<number>();
    for (let i = 0; i < 512; i++) {
      dmxValues[i] = 0;
    }
    return new Frame(-1, '', 0, new Date(2020, 10, 10 ), dmxValues);
  }

  public static fromJSON(jsonObject): Frame {
    if (!jsonObject) {
      return undefined;
    }
    return new Frame(
      jsonObject.id,
      jsonObject.name,
      jsonObject.universe,
      jsonObject.createdOn,
      jsonObject.dmxValues
    );
  }

  public static arrayFromJSON(jsonObject): Frame[] {
    let frames: Frame[] = [];
    if (!jsonObject) {
      return undefined;
    }
    for (let element of jsonObject) {
      frames.push(this.fromJSON(element));
      console.log(element);
    }
    return frames;


    }


}
