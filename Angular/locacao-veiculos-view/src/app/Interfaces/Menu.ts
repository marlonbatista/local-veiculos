export interface Menu {
    group: string;
    items: Array<MenuItem>;
  }

export interface MenuItem {
    label: string;
    url: string;
    icon: string;
}
