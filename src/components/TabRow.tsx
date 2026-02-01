'use client';

interface TabRowProps {
  tabs: string[];
  selectedTab: string | null;
  onTabSelected: (tab: string) => void;
}

export default function TabRow({ tabs, selectedTab, onTabSelected }: TabRowProps) {
  return (
    <div className="flex items-center gap-2 mb-8">
      {tabs.map((tab) => (
        <button
          key={tab}
          onClick={() => onTabSelected(tab)}
          className={`px-4 py-2 rounded-full text-sm font-medium transition-colors ${
            selectedTab === tab
              ? 'bg-primary text-white'
              : 'bg-gray-100 dark:bg-gray-800 text-content-secondary-light dark:text-content-secondary-dark hover:bg-gray-200 dark:hover:bg-gray-700'
          }`}
        >
          {tab}
        </button>
      ))}
    </div>
  );
}
