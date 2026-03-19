export interface Message {
  id: string;
  from: string;
  to?: string;
  text: string;
  timestamp: number;
  type?: string;
}

export function createMessage(from: string, text: string, to?: string): Message {
  return {
    id: `msg-${Date.now()}-${Math.random().toString(36).slice(2, 9)}`,
    from,
    to,
    text,
    timestamp: Date.now(),
    type: 'text'
  };
}
