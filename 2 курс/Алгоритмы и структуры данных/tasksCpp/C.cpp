#include <cctype>
#include <iostream>
#include <stack>
#include <string>
#include <unordered_map>
#include <vector>

using namespace std;

int main() {
  unordered_map<string, stack<int>> variables;
  vector<vector<string>> blocks;

  string line;
  while (getline(cin, line)) {
    if (line == "{") {
      blocks.push_back({});
    } else if (line == "}") {
      auto& lastBlock = blocks.back();
      for (const string& variable : lastBlock) {
        variables[variable].pop();
      }
      blocks.pop_back();
    } else {
      size_t equalSignPos = line.find('=');
      string leftVariable = line.substr(0, equalSignPos);
      string rightPart = line.substr(equalSignPos + 1);

      bool isNumber = true;
      if (rightPart.empty()) {
        isNumber = false;
      } else {
        if (rightPart[0] == '-' || isdigit(rightPart[0])) {
          for (size_t i = 1; i < rightPart.size(); ++i) {
            if (!isdigit(rightPart[i])) {
              isNumber = false;
              break;
            }
          }
        } else {
          isNumber = false;
        }
      }

      if (isNumber) {
        int numericValue = stoi(rightPart);
        if (variables.find(leftVariable) == variables.end()) {
          variables[leftVariable].push(0);
        }
        if (!blocks.empty()) {
          blocks.back().push_back(leftVariable);
        }
        variables[leftVariable].push(numericValue);
      } else {
        string rightVariable = rightPart;
        int rightVariableValue;
        if (variables.find(rightVariable) == variables.end()) {
          rightVariableValue = 0;
        } else {
          rightVariableValue = variables[rightVariable].top();
        }
        cout << rightVariableValue << '\n';

        if (leftVariable != rightVariable) {
          if (variables.find(leftVariable) == variables.end()) {
            variables[leftVariable].push(0);
          }
          if (!blocks.empty()) {
            blocks.back().push_back(leftVariable);
          }
          variables[leftVariable].push(rightVariableValue);
        }
      }
    }
  }

  return 0;
}