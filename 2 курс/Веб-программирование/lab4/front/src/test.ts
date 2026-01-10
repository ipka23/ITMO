export type PointA = {
  x: string;
  y: string;
  r: string;
  status: string;
  currentTime: string;
  executionTime: string;

}
// before adding types
const a: PointA = {x: "1", y: "2", r: "3",  status: "ok", currentTime: "now", executionTime: "0.1s"};

console.log(a.x, a.y);
