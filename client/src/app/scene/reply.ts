export class Reply {

  constructor(readonly success: boolean) {
  }

  public static fromJSON(jsonObject): Reply {
    if (!jsonObject) {
      return undefined;
    }
    return new Reply(jsonObject.success);
  }

}
