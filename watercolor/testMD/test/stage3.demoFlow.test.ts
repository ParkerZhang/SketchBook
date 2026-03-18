import { MeetingEngine } from '../sandbox/MeetingEngine';
import { AgentSandbox } from '../sandbox/AgentSandbox';

describe('Stage 3 - Demo Flow Test', () => {
  test('Full meeting lifecycle with all commands', () => {
    const engine = new MeetingEngine();

    engine.sendCommand({ type: 'start', subject: 'Project Review' });
    engine.tick();

    const meeting = engine.getMeeting('Project Review')!;

    const alice = new AgentSandbox('Alice');
    const bob = new AgentSandbox('Bob');
    const carol = new AgentSandbox('Carol');

    meeting.addAgent(alice);
    meeting.addAgent(bob);
    meeting.addAgent(carol);

    expect(meeting.log.length).toBeGreaterThan(0);
    expect(meeting.log.some(l => l.includes('joins the meeting'))).toBe(true);
    expect(meeting.log.some(l => l.includes('Nice to meet you'))).toBe(true);

    engine.sendCommand({ type: 'note', subject: 'Project Review', text: 'Discuss Q1 goals' });
    engine.tick();

    engine.sendCommand({ type: 'stop', subject: 'Project Review' });
    engine.tick();

    engine.sendCommand({ type: 'resume', subject: 'Project Review' });
    engine.tick();

    expect(meeting.log.some(l => l.includes('Meeting Resumed'))).toBe(true);
    expect(meeting.log.some(l => l.includes('is back!'))).toBe(true);

    engine.sendCommand({ type: 'end', subject: 'Project Review' });
    engine.tick();

    expect(meeting.notes.length).toBe(1);
    expect(engine.getMeeting('Project Review')).toBeUndefined();
  });
});
