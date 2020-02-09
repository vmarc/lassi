export class TimeMessage {

  constructor(readonly time: string) {
  }

  public static fromJSON(jsonObject): TimeMessage {
    if (!jsonObject) {
      return undefined;
    }
    return new TimeMessage(jsonObject.time);
  }

}
