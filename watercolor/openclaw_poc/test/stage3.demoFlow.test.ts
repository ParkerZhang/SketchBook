import { MeetingEngine } from '../sandbox/MeetingEngine';
import { AgentSandbox } from '../sandbox/AgentSandbox';

describe('Stage 3 - Demo Flow Test', () => {
  test('Full meeting lifecycle with all commands', () => {
    const engine = new MeetingEngine();

    engine.sendCommand({ type: 'start', subject: 'Project Review' });
    engine.tick();

    const meeting = engine.getMeeting('Project Review')!;
    expect(meeting.subject).toBe('Project Review');
    expect(meeting.active).toBe(true);

    const alice = new AgentSandbox('Alice');
    const bob = new AgentSandbox('Bob');
    const carol = new AgentSandbox('Carol');

    meeting.addAgent(alice);
    meeting.addAgent(bob);
    meeting.addAgent(carol);

    expect(meeting.getAgentCount()).toBe(3);
    expect(meeting.log.filter(l => l.includes('joins the meeting')).length).toBe(3);
    expect(meeting.log.filter(l => l.includes('Hello everyone!')).length).toBe(3);

    meeting.discuss(alice, 'Let\'s discuss Q1 goals.');
    meeting.discuss(bob, 'I think we should focus on feature X.');
    meeting.discuss(carol, 'Agreed, plus we need to address tech debt.');

    console.log('\n=== Meeting Log After Joins ===');
    console.log(meeting.log.join('\n'));

    engine.sendCommand({ type: 'note', subject: 'Project Review', text: 'Discuss Q1 goals' });
    engine.tick();
    expect(meeting.notes.length).toBe(1);

    engine.sendCommand({ type: 'stop', subject: 'Project Review' });
    engine.tick();
    expect(meeting.active).toBe(false);

    engine.sendCommand({ type: 'resume', subject: 'Project Review' });
    engine.tick();
    expect(meeting.active).toBe(true);

    expect(meeting.log.some(l => l.includes('Meeting Resumed'))).toBe(true);
    expect(meeting.log.filter(l => l.includes('is back!')).length).toBe(3);

    meeting.discuss(bob, 'Where were we?');
    meeting.discuss(alice, 'We were discussing Q1 priorities.');
    meeting.discuss(carol, 'Right, feature X and tech debt.');

    console.log('\n=== Final Meeting Log ===');
    console.log(meeting.log.join('\n'));

    expect(meeting.log.some(l => l.includes('discuss Q1 goals'))).toBe(true);
    expect(meeting.log.some(l => l.includes('feature X'))).toBe(true);
    expect(meeting.log.some(l => l.includes('tech debt'))).toBe(true);
    expect(meeting.log.some(l => l.includes('Where were we'))).toBe(true);

    engine.sendCommand({ type: 'end', subject: 'Project Review' });
    engine.tick();

    expect(engine.getMeeting('Project Review')).toBeUndefined();
  });
});
