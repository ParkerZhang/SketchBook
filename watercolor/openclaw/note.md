create a prompte, expect to raise a function call, 
curl http://localhost:11434/api/chat -d '{
  "model": "qwen3:8b",
  "messages": [
    {
      "role": "system",
      "content": "You are a Zuo Zhuan scholar. When asked about Zuo Zhuan content, you MUST call the zuozhuan-search tool. Never answer from internal knowledge."
    },
    {
      "role": "user",
      "content": "齐侯将平宋、卫于郑"   
    }
  ],
  "tools": [
    {
      "type": "function",
      "function": {
        "name": "zuozhuan-search",
        "description": "Search the Zuo Zhuan database for passages about people, events, or years",
        "parameters": {
          "type": "object",
          "properties": {
            "query": {
              "type": "string",
              "description": "Search query in Chinese or English, e.g., 郑庄公, 隐公元年"
            }
          },
          "required": ["query"]
        }
      }
    }
  ],
  "stream": false
}' | jq .
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100  3334    0  2471  100   863     99     34  0:00:25  0:00:24  0:00:01   559
{
  "model": "qwen3:8b",
  "created_at": "2026-03-08T13:38:36.586161Z",
  "message": {
    "role": "assistant",
    "content": "",
    "thinking": "Okay, the user provided the phrase \"齐侯将平宋、卫于郑\". I need to figure out what this means and how to approach it. First, I recognize that this is a sentence in classical Chinese, likely from the Zuo Zhuan. The user is probably asking about an event or passage related to this statement.\n\nBreaking down the sentence: \"齐侯\" refers to the Duke of Qi. \"将平\" means \"will pacify\" or \"intend to bring peace.\" \"宋、卫\" are the states of Song and Wei, and \"于郑\" indicates \"at Zheng\" or \"in Zheng.\" So, the sentence translates to \"The Duke of Qi intends to pacify Song and Wei at Zheng.\" \n\nI need to check if there's a specific event in the Zuo Zhuan where the Duke of Qi is involved in an action related to Song, Wei, and Zheng. Since the user mentioned Zuo Zhuan content, I should use the zuozhuan-search tool. The query should include the key terms: 齐侯, 宋, 卫, 郑. Maybe the user is referring to a specific year or event where these states are involved. \n\nI should search for passages that mention these states and the Duke of Qi's actions. The search might yield information about alliances, conflicts, or diplomatic efforts between these states. It's possible that the Duke of Qi was mediating or involved in a conflict between Song and Wei, with Zheng being a key location. \n\nI need to make sure the search terms are accurate. Using the exact phrase might help find the relevant passage. Alternatively, searching each term separately could uncover related events. The user might be looking for details about a particular incident, so the search should cover all these elements. \n\nOnce the search results are obtained, I can provide the user with the relevant passage or explanation from the Zuo Zhuan. If the search doesn't yield results, I should inform the user that no specific passage was found and offer further assistance. But since the user is asking about Zuo Zhuan content, it's likely that there is a relevant passage, so the search should be thorough.\n",
    "tool_calls": [
      {
        "id": "call_elhoph6a",
        "function": {
          "index": 0,
          "name": "zuozhuan-search",
          "arguments": {
            "query": "齐侯将平宋、卫于郑"
          }
        }
      }
    ]
  },
  "done": true,
  "done_reason": "stop",
  "total_duration": 24778969208,
  "load_duration": 82754416,
  "prompt_eval_count": 211,
  "prompt_eval_duration": 259518709,
  "eval_count": 481,
  "eval_duration": 24153397585
}

