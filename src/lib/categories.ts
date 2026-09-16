/**
 * 탭에 노출할 카테고리 순서. 여기 없는 카테고리는 뒤에 등장 순서대로 붙는다.
 * 목록의 첫 항목이 홈 화면의 기본 선택 탭이 된다.
 */
export const CATEGORY_ORDER = ['개발', '일상'];

export function sortCategories(categories: string[]): string[] {
  const rank = (category: string) => {
    const index = CATEGORY_ORDER.indexOf(category);
    return index === -1 ? CATEGORY_ORDER.length : index;
  };
  return [...categories].sort((a, b) => rank(a) - rank(b));
}
