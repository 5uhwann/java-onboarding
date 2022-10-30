package onboarding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Problem6 {

  private static Map<String, String> crewInfo;

  public static List<String> solution(List<List<String>> forms) {
    classifyForms(forms);
    List<String> answer = hasDuplicatedNickNameMemberEmails();
    return answer;
  }

  private static void classifyForms(List<List<String>> forms) {
    crewInfo = new HashMap<>();
    for (List<String> form : forms) {
      crewInfo.put(form.get(0), form.get(1));
    }
  }

  public static List<String> hasDuplicatedNickNameMemberEmails() {
    ArrayList<String> nickNames = new ArrayList<>(crewInfo.values());
    List<String> listOfExistWord = makeListOfExistWord(nickNames);
    List<String> listOfDuplicatedWord = makeListOfDuplicatedWord(nickNames, listOfExistWord);
    List<String> duplicatedNickNames = makeKListOfDuplicatedNickNames(nickNames,
        listOfDuplicatedWord);

    List<String> listOfDuplicatedNickNameMemberEmail = new ArrayList<>();
    for (Entry<String, String> crewMember : crewInfo.entrySet()) {
      makeListOfDuplicatedNickNameMemberEmail(duplicatedNickNames,
          listOfDuplicatedNickNameMemberEmail, crewMember);
    }
    return listOfDuplicatedNickNameMemberEmail;
  }

  /**
   * 닉네임에 포함된 모든 2글자 단어 리스트업 후 반환
   */
  private static List<String> makeListOfExistWord(List<String> nickNames) {
    ArrayList<String> listOfExistWords = new ArrayList<>();
    for (String nickName : nickNames) {
      addExistChar(listOfExistWords, nickName);
    }
    return listOfExistWords.stream().distinct().collect(Collectors.toList());
  }

  private static void addExistChar(ArrayList<String> listOfExistWord, String nickName) {
    for (int i = 0; i < nickName.length() - 1; i++) {
      String word = String.valueOf(nickName.charAt(i)) + (nickName.charAt(i + 1));
      listOfExistWord.add(word);
    }
  }

  /**
   * 중복된 단어 리스트업 후 반환
   */
  private static List<String> makeListOfDuplicatedWord(ArrayList<String> nickNames,
      List<String> listOfExistWord) {
    ArrayList<String> listOfDuplicatedWord = new ArrayList<>();
    for (String word : listOfExistWord) {
      long count = nickNames.stream()
          .filter(nickname -> nickname.contains(word))
          .count();
      if (count >= 2) {
        listOfDuplicatedWord.add(word);
      }
    }
    return listOfDuplicatedWord;
  }

  /**
   * 중복된 닉네임 리스트업 후 반환
   */
  private static ArrayList<String> makeKListOfDuplicatedNickNames(ArrayList<String> nickNames,
      List<String> listOfDuplicatedWord) {
    ArrayList<String> duplicatedNickNames = new ArrayList<>();
    for (String duplicatedWord : listOfDuplicatedWord) {
      nickNames.stream()
          .filter(nickname -> nickname.contains(duplicatedWord))
          .forEach(duplicatedNickNames::add);
    }
    return duplicatedNickNames;
  }

  /**
   * 중복된 닉네임을 가진 크루원의 이메일 리스트업 후 반환
   */
  private static void makeListOfDuplicatedNickNameMemberEmail(List<String> duplicatedNickNames,
      List<String> listOfDuplicatedNickNameMemberEmail, Entry<String, String> crewMember) {
    for (String duplicatedNickName : duplicatedNickNames) {
      if (crewMember.getValue().equals(duplicatedNickName)) {
        listOfDuplicatedNickNameMemberEmail.add(crewMember.getKey());
      }
    }
  }
}
