export interface Message {
  id: string;
  from: string;
  to?: string;
  text: string;
  timestamp: number;
  type?: string;
}
