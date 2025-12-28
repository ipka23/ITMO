export class Point {

  private readonly _x: string
  private readonly _y: string
  private readonly _r: string
  private readonly _result: string
  private readonly _currentTime: string
  private readonly _executionTime: string

  constructor(x: string, y: string, r: string, result: string, currentTime: string, executionTime: string) {
    this._x = x
    this._y = y
    this._r = r
    this._result = result
    this._currentTime = currentTime
    this._executionTime = executionTime
  }

  get x(): string {
    return this._x
  }

  get y(): string {
    return this._y
  }

  get r(): string {
    return this._r
  }

  get result(): string {
    return this._result
  }

  get currentTime(): string {
    return this._currentTime
  }

  get executionTime(): string {
    return this._executionTime
  }
}
