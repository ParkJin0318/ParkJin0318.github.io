'use client';

interface TabRowProps {
  tabs: string[];
  selectedTab: string | null;
  onTabSelected: (tab: string) => void;
}

export default function TabRow({ tabs, selectedTab, onTabSelected }: TabRowProps) {
  return (
    <div className="sticky top-[3.75rem] z-[9999] w-full bg-background-light dark:bg-background-dark px-[1.03rem]">
      <div className="flex items-center w-full">
        {tabs.map((tab) => (
          <TabItem
            key={tab}
            text={tab}
            selected={selectedTab === tab}
            onClick={() => onTabSelected(tab)}
          />
        ))}
      </div>
      <div className="w-full border-t border-divider-light dark:border-divider-dark" />
    </div>
  );
}

interface TabItemProps {
  text: string;
  selected: boolean;
  onClick: () => void;
}

function TabItem({ text, selected, onClick }: TabItemProps) {
  return (
    <div onClick={onClick} className="flex flex-col items-center cursor-pointer">
      <span
        className={`px-4 pt-6 text-[1.1rem] ${
          selected
            ? 'font-bold text-content-light dark:text-content-dark'
            : 'font-medium text-content-secondary-light dark:text-content-secondary-dark'
        }`}
      >
        {text}
      </span>
      <div
        className={`mt-2 w-full h-[0.125rem] rounded-t-[0.3rem] ${
          selected ? 'bg-content-light dark:bg-content-dark' : 'bg-transparent'
        }`}
      />
    </div>
  );
}
