// #include <iostream>
// #include <vector>
// #include <string>
//
// using namespace std;
//
// vector<int> countLettersFrequency(const vector<char>& zooList) {
//   vector<int> lettersFrequency(26, 0);
//   for (char c : zooList) {
//     int ind = tolower(c) - 'a';
//     lettersFrequency[ind]++;
//   }
//   return lettersFrequency;
// }
//
// bool zooIsValid(const string& lettersInput, const vector<char>& lettersList) {
//   if (lettersInput.length() % 2 != 0) {
//     return false;
//   }
//
//   vector<int> lettersFrequency = countLettersFrequency(lettersList);
//   for (int freq : lettersFrequency) {
//     if (freq != 2 && freq != 0) {
//       return false;
//     }
//   }
//   return true;
// }
//
// void sortZoo(const string& lettersInput) {
//   vector<char> lettersList;
//   for (char c : lettersInput) {
//     lettersList.push_back(c);
//   }
//
//   if (!zooIsValid(lettersInput, lettersList)) {
//     cout << "Impossible" << endl;
//     return;
//   }
//
//   int i = 0;
//   string answerStr;
//
//   while (!lettersList.empty()) {
//     i++;
//     answerStr += to_string(i) + " ";
//
//     int mid = lettersList.size() / 2 - 1;
//     char cage = lettersList[mid];
//     char animal = lettersList[mid + 1];
//
//     if (cage != animal && tolower(cage) == tolower(animal)) {
//       lettersList.erase(find(lettersList.begin(), lettersList.end(), cage));
//       lettersList.erase(find(lettersList.begin(), lettersList.end(), animal));
//     } else {
//       cout << "Impossible" << endl;
//       return;
//     }
//   }
//
//   cout << answerStr << endl;
// }
//
// int main() {
//   string lettersInput;
//   cin >> lettersInput;
//   sortZoo(lettersInput);
//   return 0;
// }