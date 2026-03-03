#include <iostream>
#include <string>
#include <vector>

using namespace std;

vector<int> countLettersFrequency(const vector<char>& lettersList) {
  vector<int> lettersFrequency(26, 0);
  for (char ch : lettersList) {
    int x = tolower(ch);
    if (x >= 'a' && x <= 'z') {
      lettersFrequency[x - 'a']++;
    }
  }
  return lettersFrequency;
}

bool zooIsValid(const string& lettersInput, const vector<char>& lettersList) {
  if (lettersInput.length() % 2 != 0) {
    return false;
  }

  vector<int> lettersFrequency = countLettersFrequency(lettersList);
  for (size_t i = 0; i < lettersFrequency.size(); i++) {
    if (lettersFrequency[i] != 2 && lettersFrequency[i] != 0) {
      return false;
    }
  }

  return true;
}

void sortZoo(const string& lettersInput) {
  vector<char> lettersList;
  lettersList.reserve(lettersInput.length());
  for (size_t i = 0; i < lettersInput.length(); i++) {
    lettersList.push_back(lettersInput[i]);
  }

  if (!zooIsValid(lettersInput, lettersList)) {
    cout << "Impossible";
  } else {
    string answerStr;
    while (!lettersList.empty()) {
      size_t mid = lettersList.size() / 2 - 1;
      char cage = lettersList[mid];
      char animal = lettersList[mid + 1];
      for (size_t _ = 0; _ < lettersList.size(); ++_) {
        cage = lettersList[mid];
        animal = lettersList[mid + 1];
        if (cage != animal && tolower(cage) != tolower(animal)) {
          char first = lettersList.front();
          lettersList.erase(lettersList.begin());
          lettersList.push_back(first);
        } else {
          break;
        }
      }
      if (cage != animal && tolower(cage) == tolower(animal)) {
        answerStr += to_string(tolower(lettersList[mid]) - 'a' + 1) + " ";
        lettersList.erase(lettersList.begin() + static_cast<int>(mid) + 1);
        lettersList.erase(lettersList.begin() + static_cast<int>(mid));
      } else {
        cout << "Impossible";
        return;
      }
    }
    cout << "Possible\n";
    cout << answerStr;
  }
}

int main() {
  string lettersInput;
  cin >> lettersInput;
  sortZoo(lettersInput);

  return 0;
}