export type Command =
  | { type: "start"; subject: string }
  | { type: "stop"; subject: string }
  | { type: "resume"; subject: string }
  | { type: "note"; subject: string; text: string }
  | { type: "end"; subject: string };
